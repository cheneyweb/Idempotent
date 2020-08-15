package top.xserver.idempotent.protector;

import top.xserver.idempotent.annotation.Idempotent;

public interface Protector {
    /**
     * 固定窗口
     *
     * @param key        唯一码
     * @param idempotent 幂等参数对象(duration,times)
     * @return Boolean
     */
    Boolean fixed(String key, Idempotent idempotent);

    /**
     * 滑动窗口
     *
     * @param key        唯一键
     * @param idempotent 幂等参数对象(duration,times)
     * @return Boolean
     */
    Boolean sliding(String key, Idempotent idempotent);
}
