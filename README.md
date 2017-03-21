# DragerViewLayout
拖动，拖拽视图，拖动排序

### 前言
最近有一个项目，有一个主界面，界面上有很多控件或者有多个fragment组成，大小不一，而且由于用户需要，需要自由拖动和自由组合，形成用户自己需要的组合成的模样。所以写了一个 DragerViewLayout ，只要在 DragerViewLayout 下，写入了多个视图，就可以自由拖动和组合了。DragerViewLayout 本质上是一个相对布局，所以初始位置都可以自己按相对布局的方式来定义，然后用户手动拖动后，会自动记录每个子视图的位置，进行保存，等到重新加载后，会按照记录的位置进行布局。

## 效果图
![](https://raw.githubusercontent.com/loonggg/DragerViewLayout/master/img/sss.gif)

## 使用方法

## gradle
### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2. Add the dependency
```java
	dependencies {
	    compile 'com.github.loonggg:DragerViewLayout:1.0'
	}
 ```
 
## maven
### Step 1. Add the JitPack repository to your build file  
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

### Step 2. Add the dependency
```xml
	<dependency>
	    <groupId>com.github.loonggg</groupId>
	    <artifactId>DragerViewLayout</artifactId>
	    <version>1.0</version>
	</dependency>
```

#### Example
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.loonggg.lib.DragerViewLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drager_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#654321"
        android:gravity="center"
        android:padding="10dp"
        android:tag="one"
        android:text="我是炸弹"
        android:textColor="#ffffff" />

    <fragment
        android:id="@+id/one"
        android:name="com.loonggg.dragerviewlayout.OneFragment"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_below="@+id/btn"
        android:tag="two" />

    <fragment
        android:id="@+id/three"
        android:name="com.loonggg.dragerviewlayout.ThreeFragment"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_below="@+id/btn"
        android:layout_toRightOf="@id/one"
        android:tag="three" />

    <fragment
        android:name="com.loonggg.dragerviewlayout.TwoFragment"
        android:layout_width="match_parent"
        android:layout_height="400dp"
        android:layout_below="@id/one"
        android:tag="four" />
</com.loonggg.lib.DragerViewLayout>

```

**注意：上面布局中每个控件中都必须设置tag，因为每个视图位置的移动和坐标都是通过tag来进行标记的，标记的是移动的哪个视图和控件。**

### Step 3. DragerViewLayout concrete use 
```java
public class MainActivity extends AppCompatActivity {
    TextView btn;
    private DragerViewLayout drager_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (TextView) findViewById(R.id.btn);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150);
        btn.setLayoutParams(params);
        drager_layout = (DragerViewLayout) findViewById(R.id.drager_layout);
        drager_layout.isDrager(true);//设置是否可以自动拖动视图
        drager_layout.setFilePathAndName(Environment.getExternalStorageDirectory().getPath() + "/loonggg", "settings");//设置记录保存视图位置的文件的存放位置和名字
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
            }
        });
    }
}
```
### 公众号
欢迎大家关注我的微信公众号：非著名程序员（smart_android），更多好的原创文章均首发于微信订阅号：非著名程序员
![](https://raw.githubusercontent.com/loonggg/BlogImages/master/%E5%85%AC%E4%BC%97%E5%8F%B7%E4%BA%8C%E7%BB%B4%E7%A0%81/erweima.jpg)
