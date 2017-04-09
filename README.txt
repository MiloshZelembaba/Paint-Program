* Milosh Zelembaba
* 20579583
* A2
~~~~~ Development Info
OS:  Sierra version 10.12.2
Java Version: 1.8.0_112 


~~~~   Running the game:
Just run 'make run' and that will start the paint program


~~~~  Notes:
- The "unsaved changes" warning dialog will have the options 'cancel' & 'okay' which respectivley mean
  'don't save' & 'save'


~~~~   Enhancements:
- Maintains the same aspect ratio
- Scrubber can be played forwards & backwards

  
~~~~   Overall Design:
The model stores the state of the program  (i.e color, size, etc) and the views contact the Model for these states
in which they then draw the view accordingly. The view is split to segment out the overall view, canvas, Toolbar, 
colour pallette. The controller is built into the view through action listeners that are attached to their respective
view component.
