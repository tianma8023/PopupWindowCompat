# PopupWindowCompat
处理PopupWindow在Android 7.x中兼容性问题的示例

PopupWindow 中的 showAsDropDown(View anchor) 用于在指定锚点View下方显示 PopupWindow，在Android 7.0 (api<=23) 以前是没什么问题的，但是在Android 7.x系统上，会在某些情况下出现兼容问题：

1. 如果指定 PopupWindow 的高度为 MATCH_PARENT，调用 showAsDropDown(View anchor) 时，在 7.0 之前，会在锚点 anchor 下边缘到屏幕底部之间显示 PopupWindow；而在 7.0、7.1 系统上的 PopupWindow 会占据整个屏幕（除状态栏之外）。

2. 如果指定 PopupWindow 的高度为 WRAP_CONTENT, 调用 showAsDropDown(View anchor) 时，便不会出现兼容性的问题。

3. 如果指定 PopupWindow 的高度为自定义的值height，调用 showAsDropDown(View anchor)时， 如果 height > 锚点 anchor 下边缘与屏幕底部的距离， 则还是会出现7.0、7.1上显示异常的问题；否则，不会出现该问题。可以看出，情况1和2是情况3的特例。

## 解决方案
如果出现上述分析中的兼容性问题，可以使用 showAtLocation() 方法替代 showAsDropDown() , 示例代码如下，详情可参见 [PopupWindowCompatSample](https://github.com/tianma8023/PopupWindowCompat/blob/master/app/src/main/java/com/tianma/popupwindowsample/MainActivity.java)
```java
if (Build.VERSION.SDK_INT >= 23) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
    int[] location = new int[2]; // 记录anchor在屏幕中的位置
    anchor.getLocationOnScreen(location);
    int offsetY = location[1] + anchor.getHeight();
    if (Build.VERSION.SDK_INT == 24) { // Android 7.1中，PopupWindow高度为 match_parent 时，会占据整个屏幕
        // 故而需要在 Android 7.1上再做特殊处理
        int screenHeight = ScreenUtils.getScreenHeight(context); // 获取屏幕高度
        popupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
    }
    popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, offsetY);
} else {
    popupWindow.showAsDropDown(anchor);
}
```