
attribute vec3 in_position;
attribute vec2 in_texcoords;
attribute vec3 in_normals;

uniform mat4 projectionMatrix;

varying vec2 v_vTexcoords;
varying vec3 v_vNormals;

void main()
{
	gl_Position = projectionMatrix*vec4(in_position, 1.0);
	v_vTexcoords = in_texcoords;
	v_vNormals = in_normals;
}