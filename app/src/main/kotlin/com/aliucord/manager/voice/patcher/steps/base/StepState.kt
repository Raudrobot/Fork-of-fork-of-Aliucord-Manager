package com.aliucord.manager.voice.patcher.steps.base

enum class StepState {
    Pending,
    Running,
    Success,
    Error,
    Skipped;

    val isFinished: Boolean
        get() = this == Success || this == Skipped || this == Error
}
