# Selenium Drivers

This directory contains the Selenium drivers for multiple Operating Systems
and multiple Browsers.

Before executing the test code you must install at least one Selenium driver here.


## How to Install Drivers

This section describes how to install a browser-specific driver.


### Chrome Driver

This section provides a sketch of how to install the Google Chrome browser driver.

To install Chrome [click here](https://www.google.com/chrome/dr/download)

To install Chrome driver [click here](https://developer.chrome.com/docs/chromedriver/downloads)

Also see:
 * [ChromeDriver docs](https://developer.chrome.com/docs/chromedriver)
 * [Chrome for Testing](https://googlechromelabs.github.io/chrome-for-testing/)

#### Mac OS X

Unpack the archive file and copy the driver, `chromedriver`, in the `drivers/mac/` directory.

There are times when you download executable software that the Mac OS will refuse to
run the application.  You will not need to "open" the driver app
through the _System Settings... -> Privacy -> Security section_.

[Selenium Mac Installing Chrome Driver Web Driver on Mac](https://www.youtube.com/watch?v=m4-Z5KqDHpU)
[@ 3:10]

#### Windows

Unpack the archive file and copy the driver, `chromedriver.exe`, in the `drivers/windows/` directory.

#### Linux

Unpack the archive file and copy the driver, `chromedriver`, in the `drivers/linux/` directory.


### Firefox Driver

This section provides a sketch of how to install the Mozilla Firefox browser driver.

To install Firefox [click here](https://www.mozilla.org/firefox/)

To install Firefox driver [click here](https://github.com/mozilla/geckodriver/releases)

#### Mac OS X

Unpack the archive file and copy the driver, `geckodriver`, in the `drivers/mac/` directory.

#### Windows

Unpack the archive file and copy the driver, `geckodriver.exe`, in the `drivers/windows/` directory.

#### Linux

Unpack the archive file and copy the driver, `geckodriver`, in the `drivers/linux/` directory.


### Safari Driver

This section provides a sketch of how to install the Apple Safari browser driver.

See: [Testing with WebDriver in Safari](https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari)

#### Mac OS X

The Safari browser is pre-installed as is the Selenium driver.  The code in
`BrowserType.SAFARI` points the Selenium run-time at the `/usr/bin/safaridriver` executable.

#### Windows

The Safari browser is not available on Windows.

#### Linux

The Safari browser is not available on Linux.


### MS Edge Driver

This section provides a sketch of how to install the Microsoft Edge browser driver.

To install MS Edge [click here](https://www.microsoft.com/en-us/edge)

To install MS Edge driver [click here](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver)

More documentation can be [found here](https://learn.microsoft.com/en-us/microsoft-edge/webdriver-chromium/)

#### Mac OS X

Unpack the ZIP file and copy the driver, `msedgedriver`, in the `drivers/mac/` directory.

There are no OS-specific instructions.  You will not need to "open" the driver app
through the Settings -> Privacy -> Security section.

#### Windows

Unpack the ZIP file and copy the driver, `msedgedriver.exe`, in the `drivers/windows/` directory.

#### Linux

Unpack the ZIP file and copy the driver, `msedgedriver`, in the `drivers/linux/` directory.


## Troubleshooting

This section describes various issues with Driver installation.

### Browser Version Mismatch

The most frequent problem is that the version of the browser you installed does not match
the version of the driver.  This happens a lot in Chrome and Safari if you have automatic
updates turned on.

The symptom you will see is that the driver fails to establish a "session" with the browser
application.  You will see an error message like this:

```text
SessionNotCreatedError: session not created:
 This version of ChromeDriver only supports Chrome version 85 Build info:
 version: '3.141.59', revision: 'e82be7d358', time: '2018-11-14T08:25:53'
```

You will need to discover the version number of the currently installed browser and then
use a web search to find the appropriate driver version and install that.
