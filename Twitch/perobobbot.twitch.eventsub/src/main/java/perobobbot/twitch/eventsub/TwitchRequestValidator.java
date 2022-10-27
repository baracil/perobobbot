package perobobbot.twitch.eventsub;

import fpc.tools.lang.Secret;
import io.micronaut.http.HttpRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.PerobobbotException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitchRequestValidator {

    private static final String MAC_ALGORITHM = "HmacSHA256";


    public static @NonNull Optional<TwitchRequestContent<String>> validate(@NonNull HttpRequest<?> twitchRequest, @NonNull Secret secret) {
        return new TwitchRequestValidator(twitchRequest, secret).validate();
    }

    private final @NonNull HttpRequest<?> request;
    private final @NonNull Secret secret;

    private String messageId;
    private String timeStamp;
    private String type;
    private String signature;
    private byte[] bodyContent;
    private byte[] signatureBytes;
    private String computedSignature;


    private @NonNull Optional<TwitchRequestContent<String>> validate() {
        this.retrieveTwitchHeaders();
        if (!areAllHeadersDefined()) {
            return Optional.empty();
        }
        this.readRequestBodyContent();
        this.computeMacSignatureBytes();
        this.transformSignatureBytesToString();
        if (!isRequestValid()) {
            return Optional.empty();
        }
        final var bodyAsString = new String(bodyContent, request.getCharacterEncoding());
        return Optional.of(new TwitchRequestContent<>(type, messageId, Instant.parse(timeStamp), bodyAsString));
    }

    private void retrieveTwitchHeaders() {
        messageId = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_ID.getHeader(request).orElse(null);
        timeStamp = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_TIMESTAMP.getHeader(request).orElse(null);
        type = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_TYPE.getHeader(request).orElse(null);
        signature = TwitchEventSubHeader.TWITCH_EVENTSUB_MESSAGE_SIGNATURE.getHeader(request).orElse(null);
        LOG.debug("Received request from Twitch");
        LOG.debug(" messageId : {} ", messageId);
        LOG.debug(" timeStamp : {} ", timeStamp);
        LOG.debug(" type      : {} ", type);
        LOG.debug(" signature : {} ", signature);
    }

    private boolean areAllHeadersDefined() {
        return messageId != null && timeStamp != null && type != null && signature != null;
    }


    private void readRequestBodyContent() {
        this.bodyContent = request.getBody(byte[].class).orElse(new byte[0]);
    }

    private void computeMacSignatureBytes() {
        try {
            final var mac = Mac.getInstance(MAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.US_ASCII), MAC_ALGORITHM));
            mac.update(messageId.getBytes(StandardCharsets.US_ASCII));
            mac.update(timeStamp.getBytes(StandardCharsets.US_ASCII));
            mac.update(bodyContent);
            this.signatureBytes = mac.doFinal();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new PerobobbotException("Could not find MAC algorithm " + MAC_ALGORITHM + " to check Twitch message signature", e);
        }
    }

    private void transformSignatureBytesToString() {
        computedSignature = IntStream.range(0, signatureBytes.length)
                                     .mapToObj(i -> String.format("%02x", signatureBytes[i]))
                                     .collect(Collectors.joining("", "sha256=", ""));
        LOG.debug(" Computed signature : {}", computedSignature);

    }

    private boolean isRequestValid() {
        return signature.equals(computedSignature);
    }
}
