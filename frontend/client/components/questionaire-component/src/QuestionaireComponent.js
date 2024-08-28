import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './questionaireComponent.css';
import '@eui/layout';
import QuestionComponent from '../../question-component/src/QuestionComponent';

@definition('e-questionaire-component', {
  style,
  home: 'questionaire-component',
  props: {
    parentCallbackForValidation: { type: Function },
    parentCallbackForCompletion: { type: Function },
  },
})
export default class QuestionaireComponent extends LitComponent {

  updateAnswersProvided = (index, answers) => {
    console.log("Answers Provided at index "+index+" updated from "+this.answersProvided[index]+" to "+answers);
    this.answersProvided[index] = answers;
  }

  fixButton(){
    if(this.shadowRoot.querySelector("#wizard") != null){
      if(this.shadowRoot.querySelector("#wizard").shadowRoot.querySelector("#next").shadowRoot.querySelector(".btn--reverse") != null){
        this.shadowRoot.querySelector("#wizard").shadowRoot.querySelector("#next").shadowRoot.querySelector(".btn--reverse").setAttribute("class", "btn");
      }
      if( this.shadowRoot.querySelector("#wizard").shadowRoot.querySelector("#next").shadowRoot.querySelector(".button__label") != null){
        this.shadowRoot.querySelector("#wizard").shadowRoot.querySelector("#next").shadowRoot.querySelectorAll(".button__label").forEach(e => e.remove());
        var newSpan = document.createElement('span');
        newSpan.textContent='Next';
        newSpan.className ='button__label with--icon';
        this.shadowRoot.querySelector("#wizard").shadowRoot.querySelector("#next").shadowRoot.querySelector(".btn").appendChild(newSpan)
      }
    }
  }

  generateGraphValues(){

    var categoryNames = ["Culture","Production/Service Design","Team","Process", "Architecture","Maintanence and Operations","Delivery", "Provisioning","Infrastructure"];

    var graphVals = {
      "Culture": 0,
      "Production/Service Design": 0,
      "Team": 0,
      "Process": 0,
      "Architecture":0,
      "Maintanence and Operations": 0,
      "Delivery": 0,
      "Provisioning": 0,
      "Infrastructure": 0
      };

    for(var i = 0; i < categoryNames.length; i++){    
      if(this.answersProvided[i][0]){
        graphVals[categoryNames[i]] = 3.5;
      }
      else if((this.answersProvided[i][1] || this.answersProvided[i][3]) && (!this.answersProvided[i][2] && !this.answersProvided[i][4])){
        graphVals[categoryNames[i]] = 1.5;
      }
      else if((this.answersProvided[i][2] || this.answersProvided[i][4]) && (!this.answersProvided[i][1] && !this.answersProvided[i][3])){
        graphVals[categoryNames[i]] = 2.5;
      }
      else{
        graphVals[categoryNames[i]] = 2;
      }
    }

    this.parentCallbackForCompletion(graphVals);
  }

  async getCategories(){
    fetch('http://localhost:9090/db/getCategories/')
    .then(function(response) {
      return response.json();
    }).then(function(data) {
      for(var i = 0; i < data.length; i++){
        this.categories[i] = {
          isValid: false,
          name: data[i].name,
          leadQuestion: data[i].leadQuestion.text,
          subQuestion: data[i].subQuestion.text,
          answers: data[i].subQuestion.answers
        };
      }
      this.executeRender();
    }.bind(this)).catch(function(ex) {
      console.log('An Error occurred parsing JSON!', ex);
    })
  }

  constructor(){
    super();
    this.getCategories();
  }

  categories = [];
  answersProvided = [[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]];


  validateCategory = (index, isValid) => {
    this.categories[index].isValid = isValid;
    this.executeRender();
  }

  render() {
    var validateFunc = this.validateCategory;
    var updateFunc = this.updateAnswersProvided;
    var steps = this.categories.map(function(category, i){
      return html`
        <eui-layout-v0-wizard-step 
          .valid="${category.isValid}"
          step-title="${category.name}">
          <e-question-component
            .index=${i}
            .parentCallback=${validateFunc}
            .leadQuestion=${category.leadQuestion}
            .subQuestion=${category.subQuestion} 
            .answers=${category.answers}
            .parentCallbackForUpdatingAnswersProvided=${updateFunc}>
          </e-question-component>
        </eui-layout-v0-wizard-step>
      `;
    })
     var test = html`
    <eui-layout-v0-wizard 
      slot="example"
      id="wizard"
      @eui-wizard:cancel=${this.parentCallbackForValidation}
      @eui-wizard:finish=${() => this.generateGraphValues()}>
     ${steps}
    </eui-layout-v0-wizard>
    `;
    this.fixButton();
    return test;
  }
}

QuestionaireComponent.register();
