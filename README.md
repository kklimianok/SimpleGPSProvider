# SimpleGPSProvider

### Android - Android app source. Basically, TCP Server that listen on 5897 port, read and convert nmea data and send to android as a mock (based on https://github.com/freshollie/UsbGps4Droid )
### Host - Qt application for Host OS. Basically, TCP Client, that reads the /dev/ttyUSB1 serial and send it directly to a socket connected with instance on lxc.

No config or some user-friednly experience were added, as it for testing purposes.