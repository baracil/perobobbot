package perobobbot.twitch.api.eventsub;

import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.CriteriaType;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConditionId implements CharSequence {

    private final String id;

    public ConditionId(Map<CriteriaType, String> conditions) {
        this.id = conditions.entrySet()
                            .stream()
                            .sorted(Comparator.comparing(e -> e.getKey().getIdentification()))
                            .map(e -> e.getKey() + ":" + e.getValue())
                            .collect(Collectors.joining(" "));
    }

    @Override
    public int length() {
        return id.length();
    }

    @Override
    public char charAt(int index) {
        return id.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return id.subSequence(start,end);
    }

    @Override
    public String toString() {
        return id;
    }
}
