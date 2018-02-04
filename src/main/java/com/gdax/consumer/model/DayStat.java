package com.gdax.consumer.model;

public class DayStat {

    private String open;
    private String high;
    private String low;
    private String volume;

    public DayStat() {
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DayStat{");
        sb.append("open='").append(open).append('\'');
        sb.append(", high='").append(high).append('\'');
        sb.append(", low='").append(low).append('\'');
        sb.append(", volume='").append(volume).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
