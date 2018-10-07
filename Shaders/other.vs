
attribute vec3 in_position;
attribute vec2 in_texcoords;

uniform mat4 projectionMatrix;

varying vec2 pos;

void main()
{
	gl_Position = projectionMatrix*vec4(in_position, 1.0);
	pos = vec2(in_position.x, in_position.y);
	
}