#version 400 core
in vec2 pos;

out float test;

uniform mat4 projection;
uniform mat4 transform;

void main()
{
    test = pos.x;
    gl_Position = projection * transform * vec4(pos, 0.0, 1.0);
}