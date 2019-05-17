# JavaGameEngine
It is a simple game engine using Light Weight Java Game Library.

<h1>Motivation</h1>
<p>The motivation behind this project was born from the Game Engine Game Maker Studio.</p>
<p>The Game Engine was simple to use, but had poor cpu performance when using scripts.</p>
<p>Scripts were equivalent to functions, but they were basically global functions.</p>

<p>Game Maker Studio was my first experience into programming and as I learned more about measuring performance, I found that
 the game engine just wasn't capable of doing what I needed without heavy modification.</p>
 
<p>With that in mind, I set out to create my own game engine using everything that I had learned from Game Maker Studio.</p>
<p>Since Game Maker Studio's language GML was similar to Java and many areas of the game engine had to be made by hand when
  dealing with 3D settings, It didn't seem like a very difficult task at first.</p>
 
<p>For many areas, it wasn't very difficult to implement. The most difficult area was the level editor.</p>
<p>I don't plan on finishing the level editor as I found that doing this level of work in Java is harder than I originally
  thought. The way that references work in Java make it more difficult to deal with changing things around.</p>

<p>With things like that in mind before I started, I planned to make the engine more editable for the programmers since
  there is no way to perfectly build an engine for any project.</p>

<h1>What I Learned</h1>
<p>With this project I learned a lot about handling input, different opengl calls, renderering text, resource management,
 and creating a decent game loop.</p>
<p>Examples of such are creating texture pages, creating optimal tilemaps, setting up objects that can be used in your
 game, the math behind renderering the game, the math and logic behind collision detection, and dealing with sound in an 
 useful way (allowing you to loop based on custom points).</p>
 
<p>I also learned about Java's way of optimizing. Learning that it optimizes as the program runs was interesting and made me
 not only understand Java a bit more, but respect it more as well.</p>
<p>Not everything can be used in future projects, but many elements are very important to other projects.</p>

<h1>What was used</h1>
<p>The libraries used were the Light Weight Java Game Library (LWJGL), and the Java libraries including JavaFX.<p>

<h1>Additional Notes</h1>
<p>This project will likely not be continued due to the level editor being more complicated than I originally thought.</p>
<p>Due to the engine's editability, I believe that the engine is in a good place currently. I also feel that any aditional
 edits could end up hurting my other projects.</p>

<p>I would like to work on a C++ version in the future that supports not only OpenGL, but DirectX and possibly Vulkan.</p>
<p>This version would also support a better level editor, vertex skinning, direct input, and better resource management.</p>
<p>I would also like to create at least one game with both game engines.<p>

<p>The game created with this game engine would be a 2D game that uses tilemaps, so at least tilemaps will be added to this
 engine before I start the game.</p>
