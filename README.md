# godot-date-time-picker-plugin-android

Godot plugin to display a native Android date and time picker control

To use this plugin you will need to compile it against your required version of Godot.

### Compile

You will need:

-   Android Studio https://developer.android.com/studio
-   Godot AAR Library for your desired version https://godotengine.org/download/

1. Open the project in Android Studio
2. Put your downloaded Godot AAR Library into the `app/libs` directory. (Ensure its filename matches `godot-lib*.aar`)

![image](https://user-images.githubusercontent.com/657135/207895601-72328a75-a6b7-4eab-aa4d-cdfe91f0f2a8.png)

3. Compile the project

### Install Plugin

1. Copy the `app/build/outputs/aar/DateTimePickerPlugin.aar` file and the `app/DateTimePickerPlugin.gdap` file to the `android/plugins` folder into your Godot app directory.

![image](https://user-images.githubusercontent.com/657135/207895849-9cf915a7-aa8d-43fa-bf43-8e5dd1857e24.png)

2. Go to Project -> Export, select the android export, check custom build, and enable the plugin.

![image](https://user-images.githubusercontent.com/657135/207896110-33562e9d-f3c4-4675-849d-deee614874f9.png)

### Use the control

```gdscript
extends Node2D

var dateTimePickerPlugin

func _ready():
	# Load The plugin
	dateTimePickerPlugin = Engine.get_singleton("DateTimePickerPlugin")

	# Connect to the response signal
	dateTimePickerPlugin.connect("onDateTimePicked", self, "on_DateTimePicked")

	# Show the dateTime picker for reference "onready"
	dateTimePickerPlugin.showDateTimePicker("onReady")

func on_DateTimePicked(reference : String, data : Dictionary):
	# Print the reference, response object and formated date time for each request
	# onReady {day:12, hour:23, minute:54, month:12, year:2022} 2022-12-12 23:54:00
	# buttonPressed {day:16, hour:5, minute:20, month:11, year:2025} 2025-11-16 05:20:00
	print(reference, data, Time.get_datetime_string_from_datetime_dict(data, true))

func _on_Button_pressed():
	# Show the dateTime picker for reference "buttonPressed"
	dateTimePickerPlugin.showDateTimePicker("buttonPressed")

```

![demo gif](https://user-images.githubusercontent.com/657135/207898401-b5f35348-b62e-4824-aaf4-c1ad74c0a2ee.gif)
