# VisiTrack
# A Visitor Management Android Application for Innovaccer SummerGeeks

![icon](https://user-images.githubusercontent.com/32715597/69666249-f68e5400-10b1-11ea-85ee-bb00a9d40f9f.jpg)

### Problem Statement
###### Given the visitors that we have in office and outside, there is a need to for an entry management software.

#### Technology Stack

- Android Studio
- Java
- Google Firebase Realtime Database
- Java Mail API


### Instructions to use

I have given name to the applicaton as VistTrack you can find its apk named visitrack.apk

1. Download the apk  in your phone/Tablet
2. Disable PlayProtect from PlayStore as this app is not published so Google PlayProtect may Block it from Installation
3. now you can install the app and use

### Application Flow

1. To Check in
    - Select Host if not present just click add button and add host details
	- Enter Details
	- Press Check-In button
- Email and SMS to the host will be sent and unique token number will be mailed to the user 

2. To check Out
    - Enter token number 
	- Press Check-out button

- An email to the visitor will ben send with all necessary details

### Database Structure

```
VisiTrack
   |__Visitor
        |__token
		|__Name
  		|__Phone
    		|__Email
   		|__HostName
    		|__HostPhone
    		|__HostEmail
    		|__CheckIn
    		|__CheckOut
 		|__TimeStamp
  |__Host
     |__Host ID
    	|__Name
	|__Email
  	|__Phone
```
######Note 1: Enter valid emails for testing throughout, otherwise you will not see emails being sent.

######Note 2: You can change the testing email (only gmail account for another we have to change configuration in code) which sends mail automatically in Config.java . Make sure to enable less secure apps to be able to send emails.


## Screenshots

#### Main Page (Check-in and Check-out)

![fdspeg](https://user-images.githubusercontent.com/32715597/69669452-e5484600-10b7-11ea-9e13-adb7f3e6b6d5.jpeg)


#### Select Host Page

![WhatsApp Image 2019-11-27 at 1 45 13 AM](https://user-images.githubusercontent.com/32715597/69669500-001aba80-10b8-11ea-8e52-142860704bd6.jpeg)


#### Add Host Page

![WhatsApp Image 2019-11-27 at 1 45 18 AM](https://user-images.githubusercontent.com/32715597/69669508-0315ab00-10b8-11ea-80c2-d68f010c8cf1.jpeg)

