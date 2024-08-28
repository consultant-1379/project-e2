import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './questionComponent.css';
import '@eui/layout';

@definition('e-question-component', {
  style,
  home: 'question-component',
  props: {
    index: { attribute: true, type: Number },
    leadQuestion: { attribute: true, type: String },
    subQuestion: { attribute: true, type: String },
    answers: { attribute: true, type: Array },
    parentCallback: { type: Function },
    parentCallbackForUpdatingAnswersProvided: { type: Function }
  },
})

export default class QuestionComponent extends LitComponent {

  leadSelected = [false, false];
  mainSelected = [false, false, false, false];

  selectMainAnswer(answer){
    if(this.mainSelected[parseInt(answer)]){
      this.mainSelected[answer] = false;
    }
    else{
      this.mainSelected[answer] = true;
    }
    if(this.mainSelected.includes(true)){
      this.parentCallback(this.index, true);
    }
    else{
      this.parentCallback(this.index, false)
    }
    this.executeRender();
    this.parentCallbackForUpdatingAnswersProvided(this.index, [this.leadSelected[0], this.mainSelected[0], this.mainSelected[1], this.mainSelected[2], this.mainSelected[3]])
  }

  selectLeadAnswer(yesSelected){

    if(yesSelected){
      this.leadSelected[0] = true;
      this.leadSelected[1] = false;
      this.mainSelected.fill(false);
      this.parentCallback(this.index, true);
    }
    else{
      this.leadSelected[0] = false;
      this.leadSelected[1] = true;
      this.parentCallback(this.index, false);
    }
    this.executeRender();
    this.parentCallbackForUpdatingAnswersProvided(this.index, [this.leadSelected[0], this.mainSelected[0], this.mainSelected[1], this.mainSelected[2], this.mainSelected[3]])
  }

  render() {
    return html`
   <div class="lead-q" style="float: ${ this.leadSelected[1] ? "left" : ""}">
<eui-layout-v0-tile tile-title="${this.leadQuestion}">
      <div slot="content">
        <eui-base-v0-button
        @click="${() => this.selectLeadAnswer(true)}" 
        fullwidth
        .primary="${this.leadSelected[0]}"
        >
        <span>Yes</span>
        </eui-base-v0-button>
        <eui-base-v0-button
        @click="${() => this.selectLeadAnswer(false)}" 
        fullwidth
        .primary="${this.leadSelected[1]}"
        >
        <span>No</span>
        </eui-base-v0-button>
    </div>
    </eui-layout-v0-tile>
    </div>
    <div class="main-q" style="display: ${ this.leadSelected[1] ? "block" : "none"}">
    <eui-layout-v0-tile tile-title=${this.subQuestion}>
      <div slot="content">
        <eui-base-v0-button
        @click="${() => this.selectMainAnswer(0)}" 
        align-edge 
        fullwidth
        .primary="${this.mainSelected[0]}"
        >
        <span class="answer-text">${this.answers[0]}</span>
        </eui-base-v0-button>
        <eui-base-v0-button
        @click="${() => this.selectMainAnswer(1)}" 
        align-edge 
        fullwidth
        .primary="${this.mainSelected[1]}"
        >
        <span class="answer-text">${this.answers[1]}</span>
        </eui-base-v0-button>
        <eui-base-v0-button
        @click="${() => this.selectMainAnswer(2)}" 
        align-edge 
        fullwidth
        .primary="${this.mainSelected[2]}"
        >
        <span class="answer-text">${this.answers[2]}</span>
        </eui-base-v0-button>
        <eui-base-v0-button
        @click="${() => this.selectMainAnswer(3)}" 
        align-edge 
        fullwidth
        .primary="${this.mainSelected[3]}"
        >
        <span class="answer-text">${this.answers[3]}</span>
        </eui-base-v0-button>
      </div>
    </eui-layout-v0-tile>
    </div>
   `;
  }
}

QuestionComponent.register();
