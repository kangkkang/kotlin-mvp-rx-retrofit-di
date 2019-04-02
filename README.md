# kotlin-mvp-rx-retrofit-di

Kotlin’s popularity is growing day by day. Many libraries, documents and other resources have already published with Kotlin.

So, this application will cover two design patterns: MVP and Dependency Injection. It will be abstracted by using Retrofit and RxJava2 will be used to make requests it as observables which is another hot topic for mobile development.

This project structured into 5 packages:

api: Where Retrofit resides in.

di: Where dagger2 resides in aka dependency injection.

models: Data models.

ui: Activities and also with presenter and contract.

util: Some tweaks.

Libraries & Documents:
Dagger2: GitHub - google/dagger: A fast dependency injector for Android and Java.
Retrofit: GitHub - square/retrofit: Type-safe HTTP client for Android and Java by Square, Inc.
RxJava2: GitHub - ReactiveX/RxJava: RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.
Constraint-Layout: Build a Responsive UI with ConstraintLayout | Android Developers
