#version 400 core
in vec2 passTexCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
    vec4 texColor = texture(textureSampler, passTexCoord);
    //if(texColor.a < 0.1) {
    //    discard;
    //}

    fragColor = texColor;
}