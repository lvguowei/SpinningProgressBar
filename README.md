# Give it a spin to anyting!
This is a simple android custom view library that will spin anything you pass to it.

![](https://github.com/lvguowei/SpinningProgressBar/blob/master/art/demo.gif)

## How to use

Sample code:

```xml
<com.guowei.lv.spinningprogressbarlib.SpinningProgressBar
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true"
                app:clockwise="true"
                app:cycleDuration="500"
                app:mode="linear"
                app:srcImg="@drawable/loading3" />
```

There are several properties that you can twink:

* clockwise: this is a boolean, specifies the direction of the rotation
* cycleDuration: this specifies how long one cycle of spin takes in milliseconds
* mode: there are currently 2 modes, linear and decelerate.
* srcImg: specifies the image that you want to spin

Happy spinning!
