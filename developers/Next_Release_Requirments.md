# Release 1.0 documentation
## Requirments
- [ ] A) Task creation implemented
- [ ] B) User creation implemented
- [X] C) Task completion implemented
- [ ] D) Map view implemented
- [ ] E) Chat is implemented
- [ ] F) General UI is nice and pretty
- [ ] G) Legal stuff

## Detailed feature description
### Feature A
- [X] User can specify task title, description and price
- [X] User can specify task location
- [ ] User can select a picture for the task description that would be uploaded to the back-end
- [ ] Listing saves the city of the listing

### Feature B
- [X] User can specify his email and select the passwort
- [X] User form would be validated
- [X] User can specify his name
- [ ] User can select his profile image

### Feature C
- [X] User in a role of worker can start working on the task
- [X] User in a role of job provider can finish work as done

### Feature D
- [X] Opening the map tab does not cause app crash
- [ ] User can select his city in preferences or when creating an account
- [ ] Map view by default displays users city area, or Helsinki city area
- [ ] The listings are displayed on the map as small tooltips
- [ ] User can open listing description in the bottom sheet
- [ ] User is forwarded to listing description when pulling up the bottom sheet
- [ ] User can filter tasks on the map by category

### Feature E
- [ ] Alternative 1: The chat is created between 2 users
- [ ] Alternative 2: The chat is created as a listing specific object (i.e. if I am opening the chat from the listing created by the same guy I had a chat before about another listing - it would be a new chat)
- [ ] New message in the chat creates the notification
- [ ] Chats are sorted by the timestamp of the latest message in the chat
- [ ] User can delete the chat
- [ ] Optional: In the chat there is the button that forwards the user to the listing description
- [ ] Optional: When sending the first message send a link to the listing description

### Feature F
- [ ] Fix light theme and implement the previews for the all screens (in booth dark and light themes)
- [ ] Fix buttons and text aligngment
- [ ] Fix scroll in the home screen
- [ ] Add more. . .

### Feature G
- [ ] Implement our terms for data collection and usage, and ask users consent on this
- [ ] Implement app info screen that will contain some info about the app, developers, contributors
- [ ] Implement app licenses screen that will contain licenses (if those are needed) for the used libraries and modules

<hr/>
If the release 1.0 will ever happen this file should be moved under `./release/releaze_1_0/`
