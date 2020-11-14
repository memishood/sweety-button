# Sweety Button
[![](https://jitpack.io/v/memishood/sweety-button.svg)](https://jitpack.io/#memishood/sweety-button)

![](https://github.com/memishood/sweety-button/blob/main/sweety-sample.gif)


Sweety Button is similar to the story button like on Instagram

# Download
#### 1.Add this in your root `build.gradle` at the end of repositories:
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
  
#### 2.Add this dependency in your app level `build.gradle`:
    dependencies {
        ...
        def latestVersion = "0.0.1"
        implementation "com.github.memishood:sweety-button:$latestVersion"
    }


# Usage
### 1. In your layout:
```xml
<net.memish.sweetybutton.SweetyButton
        android:id="@+id/sweetyButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:endMills="5000"
        app:buttonColor="#FF5252"
        app:progressColor="#000000"
        app:progressWidth="7"/>
```
### 2. In your Activity:

```kotlin
sweetyButton.setOnStartRecordListener {
        Toast.makeText(this, "onRecording..", Toast.LENGTH_SHORT).show()
}

sweetyButton.setOnEndRecordListener {
        Toast.makeText(this, "onStopRecording", Toast.LENGTH_SHORT).show()
}
```

## 🤝 License

```
The MIT License

Copyright (c) 2016-2020 Sweety-Button

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
