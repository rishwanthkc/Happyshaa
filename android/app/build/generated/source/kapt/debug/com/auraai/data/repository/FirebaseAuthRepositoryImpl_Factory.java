package com.auraai.data.repository;

import com.auraai.data.remote.api.AuraApiService;
import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FirebaseAuthRepositoryImpl_Factory implements Factory<FirebaseAuthRepositoryImpl> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<AuraApiService> apiServiceProvider;

  public FirebaseAuthRepositoryImpl_Factory(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<AuraApiService> apiServiceProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public FirebaseAuthRepositoryImpl get() {
    return newInstance(firebaseAuthProvider.get(), apiServiceProvider.get());
  }

  public static FirebaseAuthRepositoryImpl_Factory create(
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<AuraApiService> apiServiceProvider) {
    return new FirebaseAuthRepositoryImpl_Factory(firebaseAuthProvider, apiServiceProvider);
  }

  public static FirebaseAuthRepositoryImpl newInstance(FirebaseAuth firebaseAuth,
      AuraApiService apiService) {
    return new FirebaseAuthRepositoryImpl(firebaseAuth, apiService);
  }
}
