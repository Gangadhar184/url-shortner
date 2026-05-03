package com.example.url_shortner.util;

public class SnowflakeIdGenerator {
    private final long epoch = 1700000000000L;

    private final long machineIdBits = 10;
    private final long sequenceBits = 12;

    private final long maxMachineId = ~(-1L << machineIdBits);
    private final long maxSequence = ~(-1L << sequenceBits);

    private final long machineIdShift = sequenceBits;
    private final long timestampShift = sequenceBits + machineIdBits;

    private long machineId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long machineId) {
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("Invalid machineId: " + machineId);
        }
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long timestamp = currentTime();
        if(timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards");
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                timestamp = waitNextMillis(timestamp);
            }
        }else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - epoch) << timestampShift) | (machineId << machineIdShift) | sequence;

    }

    private long waitNextMillis(long timestamp){
        while(timestamp <= lastTimestamp){
            timestamp = currentTime();
        }
        return timestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }
}
