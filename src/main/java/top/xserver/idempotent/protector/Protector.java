package top.xserver.idempotent.protector;

public interface Protector {
    /**
     * 固定窗口
     *
     * @param key      唯一码
     * @param duration 控制时间
     * @return Boolean
     */
    Boolean fixed(String key, Long duration);

    /**
     * 滑动窗口
     *
     * @param key      唯一键
     * @param duration 时间范围
     * @return Boolean
     */
    Boolean sliding(String key, Long duration);
}
