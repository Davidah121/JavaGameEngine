
attribute vec3 in_position;
attribute vec2 in_texcoords;
attribute vec3 in_normals;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

varying vec2 v_vTexcoords;

void main()
{
	
	gl_Position = projectionMatrix*modelMatrix*vec4(in_position, 1.0);
	v_vTexcoords = in_texcoords;
}