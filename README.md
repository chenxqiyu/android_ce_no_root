 ```免root修改原理共享uid

1.mt管理器反编译要调式的apk，修改xml添加一下信息，重新打包签名安装即可。
2.自己写一个ceserver的启动app或使用agg、gg等修改器都可以
3.具体实现

1，manifest 添加android:sharedUserId
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    android:sharedUserId="com.chenx(自定义id，反编译的apk的id要相同)">

android:sharedUserId="com.chenx"

2，application要修改程序添加android:debuggable="true"
<application
        android:label="App A"
        android:icon="@mipmap/ic_launcher"
        android:debuggable="true">   

android:debuggable="true"

测试使用adb shell getenforce命令查询显示
Permissive
才可以使用agg或gg修改器
手机显示为Enforceing必须使用app的ceserver启动```

实测效果图:
<img width="3365" height="1933" alt="image" src="https://github.com/user-attachments/assets/a0b373d4-43da-4ce4-bcdb-5cebb102b3bf" />
<img width="3094" height="1379" alt="image" src="https://github.com/user-attachments/assets/f2883e44-e192-4d75-9676-676e04799af0" />
<img width="1230" height="884" alt="image" src="https://github.com/user-attachments/assets/ce1c5ef4-1f4d-4a8d-bcf0-5555e02989ba" />


