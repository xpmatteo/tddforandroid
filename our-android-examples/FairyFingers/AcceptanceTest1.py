# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
import time

# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection()

# Installs the Android package. Notice that this method returns a boolean, so you can test
# to see if the installation worked.
#Assumo che sia gia' installato da test precedenti...
#device.installPackage('AndroidDependent/build/outputs/apk/AndroidDependent-debug.apk')

#run activity on component
package = "com.tdd4android.fairyfingers"
activity = "com.tdd4android.fairyfingers.DrawActivity"
runComponent = package + "/" + activity

device.startActivity(component=runComponent)

time.sleep(.5)

# DISEGNO UN PATH
device.touch(400,400, MonkeyDevice.DOWN)
time.sleep(.05)
device.touch(410,410, MonkeyDevice.MOVE)
time.sleep(.05)
device.touch(430,410, MonkeyDevice.MOVE)
time.sleep(.05)
device.touch(430,510, MonkeyDevice.MOVE)
device.touch(430, 510, MonkeyDevice.UP)

#TEST DISEGNO
shot = device.takeSnapshot()
# Writes the screenshot to a file
# shot.writeToFile('shot1.png','png')
#Returns the single pixel at the image location (x,y), as an a tuple of integer, in the form (a,r,g,b).
(al, red, gr, bl) =shot.getRawPixel (410,410)
print "D " + str(al) + ": " + str(red) + " " + str(gr) + " " + str(bl)
assert (red<100 and gr<100 and bl>100), "non disegnato" 
print "Test disegno OK"


#TEST DECAY
time.sleep(.5)
shot = device.takeSnapshot()
(al, red, gr, bl) =shot.getRawPixel (410,410)
#shot.writeToFile('shot2.png','png')
print "D " + str(al) + ": " + str(red) + " " + str(gr) + " " + str(bl)
assert (red>200 and gr>200 and bl>200), "non cancellato" 
print "Test Cancellazione OK"


#chiudo la applicazione
device.shell('am force-stop ' + package)
