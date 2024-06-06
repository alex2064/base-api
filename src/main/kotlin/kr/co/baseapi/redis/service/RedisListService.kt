package kr.co.baseapi.redis.service

interface RedisListService {

    /**
     * 작업 대기열(Task Queue)
     * 작업 대기열은 작업을 순차적으로 처리하기 위한 구조로 먼저 추가된 작업이 먼저 처리
     */
    fun addTaskToQueue(queueName: String, task: String): Boolean

    fun getTaskFromQueue(queueName: String): String?


    /**
     * 최근 방문한 페이지 기록
     * 사용자가 최근에 방문한 페이지 목록을 저장하여 최근 기록 관리
     */
    fun addPageVisit(loginId: String, pageUrl: String): Boolean

    fun getRecentPages(loginId: String): List<String>


    /**
     * 채팅 애플리케이션의 메세지 저장
     * 채팅 애플리케이션에서 특정 채팅방의 메시지를 리스트로 저장하여 메세지 관리
     */
    fun addMsgToChatRoom(chatRoomId: String, message: String): Boolean

    fun getMsgFromChatRoom(chatRoomId: String, count: Long): List<String>


    /**
     * 주문 내역 저장
     * 전자상거래 애플리케이션에서 사용자의 주문 내역을 리스트로 저장하여 관리
     */
    fun addOrder(loginId: String, orderId: Long): Boolean

    fun getOrders(loginId: String): List<Long>
}