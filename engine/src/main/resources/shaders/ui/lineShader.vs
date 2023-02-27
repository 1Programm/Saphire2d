#version 400 core
in vec2 pos;

uniform mat4 projection;
uniform mat4 transform;

void main()
{
    gl_Position = projection * transform * vec4(pos, 0.0, 1.0);
}