package kr.co.baseapi.common.redis.enums

enum class LockType(
    val objectType: String,
    val desc: String,
    val keyName: String?
) {
    /* 예시 enum */
    APPLICATION("application", "신청", "userId"),
    CANCEL("cancel", "취소", "userId"),
    PROC("proc", "일괄작업", null);
}