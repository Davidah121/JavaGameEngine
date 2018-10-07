varying vec2 v_vTexcoords;

uniform sampler2D texture;
uniform vec4 color;

void main()
{
	gl_FragColor = texture2D(texture, v_vTexcoords)*color;
}