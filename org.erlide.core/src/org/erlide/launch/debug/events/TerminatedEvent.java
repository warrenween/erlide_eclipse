package org.erlide.launch.debug.events;

import org.eclipse.debug.core.DebugException;
import org.erlide.launch.debug.model.ErlangDebugTarget;

import com.ericsson.otp.erlang.OtpErlangPid;

public class TerminatedEvent implements DebuggerEvent {

    private final OtpErlangPid pid;

    public TerminatedEvent(final OtpErlangPid pid) {
        this.pid = pid;
    }

    @Override
    public void execute(final ErlangDebugTarget debugTarget)
            throws DebugException {
        debugTarget.terminate();
    }

}
