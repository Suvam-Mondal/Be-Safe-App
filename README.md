# Be-Safe-App

Be Safe App is an SOS App, which can send an emergency sms along with the user's location to multiple contacts on a single click. This App requires an active data connection, enabled GPS and permission to send SMS to function properly.</br>
<b>Note: 1.Cost of sending SMS will be based on your Network Provider.</br> 2. Enable all the required permissions  before using this App.</b>

## Requirements

* Android 4.0
* Permissions:
  * Location
  * Send SMS
  * Contacts
* Internet Connection

## How to use the App ?

* Install the App
* Go to System settings > Apps > Be Safe > Permissions > Check all the required permissions
* Open the App
* For first time use, you have to Setup the emergency contacts and message first.
  * Click Setup
  * Select your emergency Contacts and click Next.
  * Type in the emergency message and click Done.
  * Setup completed.
* Once the Setup is complete, you can click the SOS button to send the emergency message along with your current location to all the emergency contacts that you have selected (Cost of SMS will be based on your Network Operator).

## How it works ?

### Setting up the Emergency Contacts and Message
The App saves the emergency contacts and the emergency message in two files - one for storing the contacts and another one for storing the message. So you do not need to do the Setup process everytime you open the App.

### Sending the Emergency Message to your Emergency Contacts
On clicking the SOS button, the App fetches your current location ( Longitude, Latitude, Locality, Postal Code) and checks whether you have set up your emergency contacts and message. If not, then it asks you to set up the emergency contacts and message.
If you have already set up your emergency contacts and emergency message, it send the emergency message along with the fetched location to all your emergency contacts and shows a SMS sent message if sending was successful, else it will show an sending failed message.

## Screenshots

<img src="https://github.com/Suvam-Mondal/Be-Safe-App/blob/master/img1.png" height="480" width="320">

<img src="https://github.com/Suvam-Mondal/Be-Safe-App/blob/master/img2.png" height="480" width="320">
