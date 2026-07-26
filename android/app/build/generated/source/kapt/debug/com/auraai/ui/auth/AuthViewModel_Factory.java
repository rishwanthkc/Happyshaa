package com.auraai.ui.auth;

import com.auraai.data.local.preferences.PreferenceManager;
import com.auraai.domain.usecase.GetSessionUseCase;
import com.auraai.domain.usecase.SendPasswordResetUseCase;
import com.auraai.domain.usecase.SignInUseCase;
import com.auraai.domain.usecase.SignInWithGoogleUseCase;
import com.auraai.domain.usecase.SignOutUseCase;
import com.auraai.domain.usecase.SignUpUseCase;
import com.auraai.domain.usecase.SyncUserUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<SignInUseCase> signInUseCaseProvider;

  private final Provider<SignUpUseCase> signUpUseCaseProvider;

  private final Provider<SignOutUseCase> signOutUseCaseProvider;

  private final Provider<SendPasswordResetUseCase> sendPasswordResetUseCaseProvider;

  private final Provider<SignInWithGoogleUseCase> signInWithGoogleUseCaseProvider;

  private final Provider<SyncUserUseCase> syncUserUseCaseProvider;

  private final Provider<PreferenceManager> preferenceManagerProvider;

  private final Provider<GetSessionUseCase> getSessionUseCaseProvider;

  public AuthViewModel_Factory(Provider<SignInUseCase> signInUseCaseProvider,
      Provider<SignUpUseCase> signUpUseCaseProvider,
      Provider<SignOutUseCase> signOutUseCaseProvider,
      Provider<SendPasswordResetUseCase> sendPasswordResetUseCaseProvider,
      Provider<SignInWithGoogleUseCase> signInWithGoogleUseCaseProvider,
      Provider<SyncUserUseCase> syncUserUseCaseProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<GetSessionUseCase> getSessionUseCaseProvider) {
    this.signInUseCaseProvider = signInUseCaseProvider;
    this.signUpUseCaseProvider = signUpUseCaseProvider;
    this.signOutUseCaseProvider = signOutUseCaseProvider;
    this.sendPasswordResetUseCaseProvider = sendPasswordResetUseCaseProvider;
    this.signInWithGoogleUseCaseProvider = signInWithGoogleUseCaseProvider;
    this.syncUserUseCaseProvider = syncUserUseCaseProvider;
    this.preferenceManagerProvider = preferenceManagerProvider;
    this.getSessionUseCaseProvider = getSessionUseCaseProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(signInUseCaseProvider.get(), signUpUseCaseProvider.get(), signOutUseCaseProvider.get(), sendPasswordResetUseCaseProvider.get(), signInWithGoogleUseCaseProvider.get(), syncUserUseCaseProvider.get(), preferenceManagerProvider.get(), getSessionUseCaseProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<SignInUseCase> signInUseCaseProvider,
      Provider<SignUpUseCase> signUpUseCaseProvider,
      Provider<SignOutUseCase> signOutUseCaseProvider,
      Provider<SendPasswordResetUseCase> sendPasswordResetUseCaseProvider,
      Provider<SignInWithGoogleUseCase> signInWithGoogleUseCaseProvider,
      Provider<SyncUserUseCase> syncUserUseCaseProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<GetSessionUseCase> getSessionUseCaseProvider) {
    return new AuthViewModel_Factory(signInUseCaseProvider, signUpUseCaseProvider, signOutUseCaseProvider, sendPasswordResetUseCaseProvider, signInWithGoogleUseCaseProvider, syncUserUseCaseProvider, preferenceManagerProvider, getSessionUseCaseProvider);
  }

  public static AuthViewModel newInstance(SignInUseCase signInUseCase, SignUpUseCase signUpUseCase,
      SignOutUseCase signOutUseCase, SendPasswordResetUseCase sendPasswordResetUseCase,
      SignInWithGoogleUseCase signInWithGoogleUseCase, SyncUserUseCase syncUserUseCase,
      PreferenceManager preferenceManager, GetSessionUseCase getSessionUseCase) {
    return new AuthViewModel(signInUseCase, signUpUseCase, signOutUseCase, sendPasswordResetUseCase, signInWithGoogleUseCase, syncUserUseCase, preferenceManager, getSessionUseCase);
  }
}
