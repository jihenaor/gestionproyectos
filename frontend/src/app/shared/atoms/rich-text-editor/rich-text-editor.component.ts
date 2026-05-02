import { Component, input, output, signal, forwardRef, ElementRef, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-rich-text-editor',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="rich-editor" [class.rich-editor--resizable]="verticalResize()">
      <div class="rich-editor__toolbar">
        <button type="button" class="rich-editor__btn" (click)="execCommand('bold')" title="Negrita">
          <strong>B</strong>
        </button>
        <button type="button" class="rich-editor__btn" (click)="execCommand('italic')" title="Cursiva">
          <em>I</em>
        </button>
        <button type="button" class="rich-editor__btn" (click)="execCommand('underline')" title="Subrayado">
          <u>U</u>
        </button>
        <span class="rich-editor__separator"></span>
        <button type="button" class="rich-editor__btn" (click)="execCommand('insertUnorderedList')" title="Lista con viñetas">
          <span>&#8226;</span>
        </button>
        <button type="button" class="rich-editor__btn" (click)="execCommand('insertOrderedList')" title="Lista numerada">
          <span>1.</span>
        </button>
        <span class="rich-editor__separator"></span>
        <button type="button" class="rich-editor__btn" (click)="execCommand('formatBlockparagraph')" title="Párrafo">
          P
        </button>
        <button type="button" class="rich-editor__btn" (click)="execCommand('formatBlockh3')" title="Encabezado 3">
          H3
        </button>
      </div>
      <div
        #editorElement
        class="rich-editor__content"
        [attr.contenteditable]="!isDisabled"
        (input)="onInput($event)"
        (blur)="onBlur()"
        [attr.data-placeholder]="placeholder()"
      ></div>
      @if (showCharCount()) {
        <div class="rich-editor__footer">
          <span class="rich-editor__count">{{ characterCount() }} / {{ maxLength() }}</span>
        </div>
      }
    </div>
  `,
  styles: [`
    :host {
      display: block;
      width: 100%;
      min-width: 0;
    }
    .rich-editor {
      display: flex;
      flex-direction: column;
      width: 100%;
      min-width: 0;
      min-height: 14rem;
      max-height: min(65vh, 28rem);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      overflow: hidden;
      background: var(--color-background);
    }
    .rich-editor:focus-within {
      border-color: var(--color-primary);
    }
    .rich-editor--resizable {
      overflow: auto;
      resize: vertical;
      max-height: min(95vh, 60rem);
    }
    .rich-editor__toolbar {
      flex-shrink: 0;
      display: flex;
      align-items: center;
      gap: var(--spacing-xs);
      padding: var(--spacing-sm);
      background: var(--color-background-secondary);
      border-bottom: 1px solid var(--color-border);
      flex-wrap: wrap;
    }
    .rich-editor__btn {
      padding: var(--spacing-xs) var(--spacing-sm);
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-sm);
      cursor: pointer;
      font-size: 0.875rem;
      min-width: 32px;
      height: 32px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .rich-editor__btn:hover {
      background: var(--color-background-tertiary);
    }
    .rich-editor__btn:active {
      background: var(--color-primary);
      color: white;
    }
    .rich-editor__separator {
      width: 1px;
      height: 20px;
      background: var(--color-border);
      margin: 0 var(--spacing-xs);
    }
    .rich-editor__content {
      flex: 1 1 auto;
      min-height: 0;
      padding: var(--spacing-md);
      outline: none;
      font-size: 1rem;
      line-height: 1.65;
      overflow-y: auto;
      overflow-x: hidden;
      overscroll-behavior: contain;
    }
    .rich-editor__content:empty:before {
      content: attr(data-placeholder);
      color: var(--color-text-secondary);
    }
    .rich-editor__content ul, .rich-editor__content ol {
      margin: var(--spacing-sm) 0;
      padding-left: var(--spacing-lg);
    }
    .rich-editor__content li {
      margin-bottom: var(--spacing-xs);
    }
    .rich-editor__footer {
      flex-shrink: 0;
      display: flex;
      justify-content: flex-end;
      padding: var(--spacing-xs) var(--spacing-sm);
      background: var(--color-background-secondary);
      border-top: 1px solid var(--color-border);
    }
    .rich-editor__count {
      font-size: 0.75rem;
      color: var(--color-text-secondary);
    }
  `],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => RichTextEditorComponent),
      multi: true
    }
  ]
})
export class RichTextEditorComponent implements ControlValueAccessor, AfterViewInit, OnDestroy {
  @ViewChild('editorElement') editorElementRef!: ElementRef<HTMLDivElement>;

  placeholder = input<string>('');
  maxLength = input<number>(4000);
  showCharCount = input<boolean>(true);
  disabled = input<boolean>(false);
  /** Permite arrastrar el borde inferior para cambiar la altura del editor. */
  verticalResize = input<boolean>(false);

  valueChange = output<string>();
  characterCount = signal(0);
  isDisabled = false;

  private pendingValue: string | null = null;
  private onChange: (value: string) => void = () => {};
  private onTouched: () => void = () => {};

  ngAfterViewInit(): void {
    if (this.pendingValue !== null) {
      this.setEditorContent(this.pendingValue);
      this.pendingValue = null;
    } else if (this.editorElementRef?.nativeElement) {
      this.updateCharacterCount();
    }
  }

  ngOnDestroy(): void {
    // Cleanup if needed
  }

  execCommand(command: string, value?: string): void {
    document.execCommand(command, false, value);
    this.updateValue();
  }

  onInput(event: Event): void {
    this.updateValue();
  }

  onBlur(): void {
    this.onTouched();
  }

  private updateValue(): void {
    const el = this.editorElementRef?.nativeElement;
    if (el) {
      const html = el.innerHTML;
      this.updateCharacterCount();
      this.onChange(html);
      this.valueChange.emit(html);
    }
  }

  private updateCharacterCount(): void {
    const el = this.editorElementRef?.nativeElement;
    if (el) {
      this.characterCount.set(el.textContent?.length || 0);
    }
  }

  private setEditorContent(value: string): void {
    const el = this.editorElementRef?.nativeElement;
    if (el) {
      el.innerHTML = value || '';
      this.updateCharacterCount();
    }
  }

  writeValue(value: string): void {
    if (this.editorElementRef?.nativeElement) {
      this.setEditorContent(value);
    } else {
      this.pendingValue = value || '';
    }
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
  }
}