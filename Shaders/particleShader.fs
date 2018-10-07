
varying vec2 v_vTexcoords;
varying vec4 v_vColor;

uniform sampler2D particleTexture;

void main()
{
	gl_FragColor = texture2D(particleTexture,v_vTexcoords) * v_vColor;
}