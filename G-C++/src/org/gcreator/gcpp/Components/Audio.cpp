#ifndef _GCPP_COMPONENTS_AUDIO_
#define _GCPP_COMPONENTS_AUDIO_

#include "../compilers/declare.h"

org::gcreator::Components::Audio::Audio();
{
    sound=NULL;
}

org::gcreator::Components::Audio::Audio(Mix_Music* _s);
{
    sound=_s;
}

org::gcreator::Components::Audio::Audio(std::string file);
{
    sound=loadAudio(file);
}

bool org::gcreator::Components::Audio::loadAudio(std::string file);
{
    sound=Mix_LoadMUS(file);
    if (getAudio()==NULL)
    {
        return false;
    }
    else
    {
        return true;
    }
}

bool org::gcreator::Components::Audio::setAudio(Mix_Music* _s);
{
    sound=_s
    if (getAudio()==NULL)
    {
        return false;
    }
    else
    {
        return true;
    }
}

Mix_Music* org::gcreator::Components::Audio::getAudio();
{
    return sound;
}

bool org::gcreator::Components::Audio::audioPlaying();
{
    return (Mix_PlayingMusic());
}

void org::gcreator::Components::Audio::playAudio(int num);
{
    Mix_PlayMusic(sound,num);
}

void org::gcreator::Components::Audio::pauseAudio();
{
    Mix_PauseMusic();
}

void org::gcreator::Components::Audio::unpauseAudio();
{
    Mix_ResumeMusic();
}

bool org::gcreator::Components::Audio::audioPaused();
{
    return (Mix_PausedMusic());
}

void org::gcreator::Components::Audio::stopAudio();
{
    Mix_HaltMusic();
}

#endif
