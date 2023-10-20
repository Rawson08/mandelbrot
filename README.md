## Name
Project 1: Modulo Times Tables Visualization

## Description
This code is a JavaFX application that implements a mathematical animation called "The Mathologer". The animation displays a table of angles and the respective sine, cosine, and tangent values for those angles. The user can control the number of angles to display, the increment size of the angles, the frame rate, and start/stop the animation.

The UI elements include a canvas for displaying the animation, a start/stop button, a text field for entering the number of angles, a label for the number of angles, a text field for entering the increment size, a label for the increment size, a slider for setting the frame rate, a label for the frame rate, a text field for jumping to a specific times table, and a label for the times table jump.

The AnimationTimer class is used to refresh the animation at a specified frame rate. The handle method checks if enough time has elapsed since the last frame was displayed, and if so, it updates the animation and sets the last time to the current time. The animation is updated by incrementing the factor and then recalculating the sine, cosine, and tangent values for each angle. The color of each line is also updated using a color array.