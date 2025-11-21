![输入图片说明](https://images.gitee.com/uploads/images/2020/0329/060357_d50dc364_899222.jpeg "QQ截图20200329060210.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0329/060408_475d69a4_899222.jpeg "QQ截图20200329060118.jpg")
 支持一下 :smile: 

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
手机显示为Enforceing必须使用app的ceserver启动
```

实测效果图:
<img width="3365" height="1933" alt="image" src="https://github.com/user-attachments/assets/a0b373d4-43da-4ce4-bcdb-5cebb102b3bf" />
<img width="3094" height="1379" alt="image" src="https://github.com/user-attachments/assets/f2883e44-e192-4d75-9676-676e04799af0" />
VR设备实测:
<img width="2712" height="954" alt="image" src="https://github.com/user-attachments/assets/e297654a-c5bc-417f-995f-fafafb8d0f72" />
<img width="1565" height="1685" alt="image" src="https://github.com/user-attachments/assets/6083132b-5b35-40fa-9e37-e7653dbb592f" />
手机实测:
<img width="1230" height="884" alt="image" src="https://github.com/user-attachments/assets/ce1c5ef4-1f4d-4a8d-bcf0-5555e02989ba" />

```
3.0版本支持fridaserver17.3.2
```
```
需要使用usb数据线连接电脑
1.启动frida-server
2.端口转发
adb forward tcp:27042 tcp:27042
adb forward tcp:52736 tcp:52736
3.查询目标包的pid
adb shell pidof com.example.mytestapp
4.电脑连接
F:\Anaconda3\envs\frida\Scripts\frida.exe -U -p 31447 -l F:\core\frida\x86\chrometest1.js
```
<img width="896" height="89" alt="image" src="https://github.com/user-attachments/assets/178b73d6-5240-4035-aee7-e0a142274428" />
<img width="470" height="114" alt="image" src="https://github.com/user-attachments/assets/7632177b-97b2-4a3c-9678-02ec2377bc3b" />
<img width="1610" height="605" alt="image" src="https://github.com/user-attachments/assets/e8c4eb37-150c-481e-8393-0db875fc8bd2" />
<img width="782" height="1648" alt="image" src="https://github.com/user-attachments/assets/3267fc39-5483-4c4a-b792-61edf89cd22e" />


