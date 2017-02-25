package asia.corelogic.hackathon.gateway;

import java.util.Objects;

public class RangeParam {
    public static final RangeParam EMPTY = new RangeParam((Long) null, (Long) null);
    private final Long min;
    private final Long max;
    public RangeParam(Long min, Long max) {
        this.min = min;
        this.max = max;
    }
    public RangeParam(Integer min, Integer max) {
        this(toLong(min), toLong(max));
    }
    @Override
    public String toString() {
        return String.format("%s-%s", Objects.toString(min, "0"), Objects.toString(max, ""));
    }
    public boolean isProvided() {
        return min != null || max != null;
    }
    private static Long toLong(Integer value) {
        return value == null ? null : value.longValue();
    }
}
