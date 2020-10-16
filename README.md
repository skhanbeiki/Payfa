# Payfa
[![](https://jitpack.io/v/skhanbeiki/Payfa.svg)](https://jitpack.io/#skhanbeiki/Payfa)
##### An online payment SDK for android

[Payfa website](https://payfa.com/)

Payfa is a quick and easy library for projects that require a payment gateway.

 ![alt text](https://github.com/skhanbeiki/Payfa/blob/master/images/head.png)
 
Payfa supports Google Chrome and the internal browser, you can change the color of the payment page to use the built-in browser.
It is better to use Google Chrome to publish in markets that do not accept the internal browser.
If you want to use the internal browser, you can explain to your market support.


# Attributes
+ Fast
+ Easy
+ High Security
+ Android X

# Can be used in 
```
minSdkVersion 14
```

# Download
You can download a jar from GitHub's [releases page.](https://github.com/skhanbeiki/Payfa/releases)

Or use Gradle:
```java
compileOptions {
      sourceCompatibility JavaVersion.VERSION_1_8
      targetCompatibility JavaVersion.VERSION_1_8
}
```

```java
repositories {
   maven { url 'https://jitpack.io' }
}

dependencies {
   implementation 'com.github.skhanbeiki:Payfa:1.3'
}
```
Or Maven:
```java
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
    <dependency>
	    <groupId>com.github.skhanbeiki</groupId>
	    <artifactId>Payfa</artifactId>
	    <version>1.0</version>
	</dependency>
```
# How to use

Request :
```java
new Payfa().init(API, ActRequest.this)
                        .amount(1100, Currency.toman)
                        .invoice(new Random().nextInt(1256325))
                        .listener(ActRequest.this)
                        .internalBrowser(false)
                        .requestCode(1371)
                        .nameAndDetails("name", "details")
                        .build();
			
    @Override
    public void onLaunch() {
        Log.i("TextProject", "onLaunch");
    }

    @Override
    public void onFailure(ErrorModel errorModel) {
        Log.i("TextProject", errorModel.msg + "   " + errorModel.code);
    }

    @Override
    public void onFinish(String paymentId) {
        Log.i("TextProject", "onFinish = " + paymentId);
    }
```
Status :
```java
 new Payfa().payStatus(API, getApplicationContext(), ActVerify.this);
 
   @Override
    public void onFailure(ErrorModel errorModel) {

    }

    @Override
    public void onFinish(Status status) {

    }
```
Or :
```java
new Payfa().payStatus(API, "payment_id", ActVerify.this);

   @Override
    public void onFailure(ErrorModel errorModel) {

    }

    @Override
    public void onFinish(Status status) {

    }
```

manifest :

To return from the Bank page to the desired activity
```java
<intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data
                    android:host="@string/host1"
                    android:scheme="http" />
</intent-filter>
```
String File :
```
<string name="host1">payfa.com</string>
```
# library in use

```java
    implementation 'org.jetbrains:annotations:16.0.1'
    implementation 'androidx.browser:browser:1.2.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

# Author

[Moslem Khanbeiki](http://khanbeiki.ir/)

# Thanks

[Retrofit](https://square.github.io/retrofit/)

# Disclaimer

This is not an official Google product.

    
