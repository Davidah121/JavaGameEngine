
attribute vec3 in_position;
attribute vec2 in_texcoords;
//attribute vec3 in_normals;
attribute vec4 in_colors;

uniform mat4 projectionMatrix;

varying vec2 v_vTexcoords;
varying vec4 v_vColor;
void main()
{
	gl_Position = projectionMatrix*vec4(in_position, 1.0);
	
	v_vTexcoords = in_texcoords;
	//vec3 n = in_normals;
	v_vColor = in_colors;
}