package perobobbot.twitch.web.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import perobobbot.oauth.api.HeaderHolder;

@Filter("/**")
@RequiredArgsConstructor
public class TwitchClientFilter implements HttpClientFilter {

    private final HeaderHolder headerHolder;

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        final var headers = request.getHeaders();
        headerHolder.setHeaders(headers);
        return chain.proceed(request);
    }
}
