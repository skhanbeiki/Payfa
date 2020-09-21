# Payfa
[![](https://jitpack.io/v/skhanbeiki/Payfa.svg)](https://jitpack.io/#skhanbeiki/Payfa)
##### An online payment SDK for android

[Payfa website](https://payfa.com/)

Payfa is a quick and easy library for projects that require a payment gateway.

 ![alt text](https://github.com/skhanbeiki/Payfa/blob/master/images/head.png)
 
Pfa supports Google Chrome and the internal browser, you can change the color of the payment page to use the built-in browser.
It is better to use Google Chrome to publish in markets that do not accept the internal browser.
If you want to use the internal browser, you can explain to your market support.


# Attributes
+ Fast
+ Easy
+ high security

# Can be used in 
```
minSdkVersion 16
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
maven { url 'https://jitpack.io' }

implementation 'com.github.skhanbeiki:Payfa:1.0'
```
Or Maven:
```
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
# library in use

```java
    implementation 'org.jetbrains:annotations:16.0.1'
    implementation 'androidx.browser:browser:1.2.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

# Author

[mmoslem khanbeiki](http://khanbeiki.ir/)

# Thanks

[retrofit](https://square.github.io/retrofit/)

# Disclaimer

This is not an official Google product.

    
