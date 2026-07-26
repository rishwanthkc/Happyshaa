package com.auraai.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ,\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J$\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\t2\u0006\u0010\u000b\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0016"}, d2 = {"Lcom/auraai/data/repository/NotificationsRepositoryImpl;", "Lcom/auraai/domain/repository/NotificationsRepository;", "apiService", "Lcom/auraai/data/remote/api/AuraApiService;", "(Lcom/auraai/data/remote/api/AuraApiService;)V", "offlineNotifications", "", "Lcom/auraai/domain/model/NotificationItem;", "getNotificationHistory", "Lkotlin/Result;", "", "token", "", "getNotificationHistory-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveFcmToken", "", "fcmToken", "saveFcmToken-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendTestNotification", "sendTestNotification-gIAlu-s", "app_debug"})
public final class NotificationsRepositoryImpl implements com.auraai.domain.repository.NotificationsRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.remote.api.AuraApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.auraai.domain.model.NotificationItem> offlineNotifications = null;
    
    @javax.inject.Inject()
    public NotificationsRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.AuraApiService apiService) {
        super();
    }
}