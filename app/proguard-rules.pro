#############################################
#
# 对于一些基本指令的添加
#
#############################################
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 这句话能够使我们的项目混淆后产生映射文件 包含有类名->混淆后类名的映射关系
-verbose
-dontwarn

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
#-dontshrink
#-dontoptimize
-ignorewarnings

# 不混淆 Annotation ; Signature{泛型} |  SourceFile LineNumberTable 抛出异常时保留代码行号
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
-keep class * extends android.app.backup.BackupAgentHelper
-keep class * extends android.preference.Preference
-keep class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService
-dontwarn android.support.**

-keep public class * extends android.view.View {
     public <init>(android.content.Context);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
     public void set*(...);
 }


-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
  public static <fields>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
    <init>(org.json.JSONObject);
}
-keep class org.json.**{*;}

-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
}

###################################################################################################
###################################################################################################

#apache
-keep class org.apache.http.**{ *;}

#tencent bugly
-keep public class com.tencent.bugly.**{*;}

#ShareSDK
-keep class m.framework.**{ *;}
-keep class cn.sharesdk.**{ *;}

#支付宝支付
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

-keepattributes *Annotation*,EnclosingMethod,Signature
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#RxJava
-keep class rx.**{*; }
-dontwarn rx.**

##---------------Begin: proguard configuration for Gson ----------
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}
-keep class **.bean.**{*;}
-keepattributes Signature
-keepattributes *Annotation*
##---------------End: proguard configuration for Gson ----------

#okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

#JPUSH
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}
-keep class com.google.gson.** {*;}

#baseAdapter
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

#utilcode
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**

#retrofit2 begin
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#retroit2 end

#Glide begin
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#如果你的 target API 低于 Android API 27，请添加：
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
# VideoDecoder 使用 API 27 的一些接口，这可能导致 proguard 发出警告，尽管这些 API 在旧版 Android 设备上根本不会被调用。
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#Glide end

#arouter begin
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider
#arouter end

#############################################
#
# 关键字                       含义

#  keep                       保留类和类成员，防止被混淆或移除

#  Keepnames                  保留类和类成员，防止被混淆，但没有被引用的类成员会被移除

#  keepclassmembers           只保留类成员，防止被混淆或移除

#  keepclassmembernames       只保留类成员，防止被混淆，但没有被引用的成员会被移除

#  keepclasseswithmembers     保留类和类成员，防止被混淆或移除，如果指定的类成员不存在还是会被混淆

#  keepclasseswithmembernames 保留类和类成员，防止被混淆，如果指定的类成员不存在还是会被混淆，没有被引用的类成员会被移除



#  通配符          含义

#   *             匹配任意长度字符，但不含包名分隔符.。例如一个类的全包名路径是com.othershe.test.Person，使用com.othershe.test.*、com.othershe.test.*都是可以匹配的，但com.othershe.*就不能匹配

#   **            匹配任意长度字符，并包含包名分隔符.。例如要匹配com.othershe.test.**包下的所有内容

#   ***           匹配任意参数类型。例如*** getName(***)可匹配String getName(String)

#   ...           匹配任意长度的任意类型参数。例如void setName(...)可匹配void setName(String firstName, String secondName)

#   <fileds>      匹配类、接口中所有字段

#   <methods>     匹配类、接口中所有方法

#   <init>        匹配类中所有构造函数

#############################################
