#version 400 core

in vec2 passTexCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec4 color;

void main()
{
    vec4 texColor = texture(textureSampler, passTexCoord);
    fragColor = vec4(color.rgb, color.a * texColor.a);
}