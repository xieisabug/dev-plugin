package com.xjy.dev;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DevKeyAction extends EditorAction {

    protected DevKeyAction() {
        super(new Handler());
    }

    private static class Handler extends EditorActionHandler {
        @Override
        protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            String command = editor.getSelectionModel().getSelectedText();
            System.out.println(command);
            if (command != null) {
                if (command.startsWith("cp")) {
                    String[] lines = command.substring(2).split("\\+");
                    if (lines.length == 2) {
                        int start = Integer.parseInt(lines[0]);
                        int end = Integer.parseInt(lines[1]);
                        Document document = editor.getDocument();
                        int selectionStart = editor.getSelectionModel().getSelectionStart();
                        int selectionEnd = editor.getSelectionModel().getSelectionEnd();
                        int lineStartOffset = document.getLineStartOffset(start - 1);
                        int lineEndOffset = document.getLineEndOffset(end - 1);
                        TextRange textRange = TextRange.create(lineStartOffset, lineEndOffset);
                        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> document.replaceString(selectionStart, selectionEnd, document.getText(textRange)));
                    } else if (lines.length == 1) {
                        int start = Integer.parseInt(lines[0]);
                        Document document = editor.getDocument();
                        int selectionStart = editor.getSelectionModel().getSelectionStart();
                        int selectionEnd = editor.getSelectionModel().getSelectionEnd();
                        int lineStartOffset = document.getLineStartOffset(start - 1);
                        int lineEndOffset = document.getLineEndOffset(start - 1);
                        TextRange textRange = TextRange.create(lineStartOffset, lineEndOffset);
                        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> document.replaceString(selectionStart, selectionEnd, document.getText(textRange)));
                    }
                }
            }
        }
    }
}
