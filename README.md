# PopupWindowCompat
处理PopupWindow在Android 7.x中兼容性问题的示例

PopupWindow 中的 showAsDropDown(View anchor) 用于在指定锚点View下方显示 PopupWindow, 在Android 7.0 (api<=23) 以前是没什么问题的，但是在Android 7.x系统上，会在某些情况下出现兼容问题。
