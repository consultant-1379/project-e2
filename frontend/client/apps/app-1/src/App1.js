import { definition } from '@eui/component';
import { App, html } from '@eui/app';
import style from './app1.css';
import QuestionaireComponent from '../../../components/questionaire-component/src/QuestionaireComponent';
import StartComponent from '../../../components/start-component/src/StartComponent';
import GraphComponent from '../../../components/graph-component/src/GraphComponent';
import ResultsComponent from '../../../components/results-component/src/ResultsComponent';
import '@eui/layout';

@definition('e-app-1', {
  style,
  props: {
    response: { attribute: false },
  },
})
export default class App1 extends App {

  hasStarted = false;
  hasFinished = false;
  graphAnswers = {
    "Culture": 0,
    "Production/Service Design": 0,
    "Team": 0,
    "Process": 0,
    "Architecture": 0,
    "Maintanence and Operations": 0,
    "Delivery": 0,
    "Provisioning": 0,
    "Infrastructure": 0
    };
  graphTimestamp = ""

  startQuestionaire = () => {
    this.hasStarted = true;
    this.executeRender();
  }

  async submitQuestionaire(answers){
    fetch('http://localhost:9090/db/getUserByEmail/'+localStorage.getItem('username'))
    .then(function(response) {
      return response.json();
    }).then(function(data) {
      fetch("http://localhost:9090/db/submitUserAnswers/"+data.id, {
        method: "POST",
        headers: {'Content-Type': 'application/json'}, 
        body: JSON.stringify({ "answers": answers })
      }).then(() => {
        console.log("Answers submitted for user: "+data.id);
      });
    }.bind(this)).catch(function(ex) {
      console.log('User not found!', ex);
    });
  }

  endQuestionaire = (answers) => {
    this.hasFinished = true;
    var time = new Date();
    this.graphAnswers = answers;
    this.graphTimestamp = time.toUTCString();
    this.executeRender();
    this.submitQuestionaire(answers);
  }

  cancelClick = () => {
    this.hasStarted = false;
    this.hasFinished = false;
    this.executeRender();
  }

  render() {
    const { EUI } = window;
    if(this.hasStarted && !this.hasFinished){
      return html`<e-questionaire-component .parentCallbackForValidation=${this.cancelClick} .parentCallbackForCompletion=${this.endQuestionaire}></e-questionaire-component>`;
    }
    else if(!this.hasStarted){
      return html`<e-start-component .parentCallback=${this.startQuestionaire}></e-start-component>`;
    }
    else if(this.hasStarted && this.hasFinished){
      return html`<e-results-component .answers=${this.graphAnswers} .timestamp=${this.graphTimestamp} .parentCallbackForHome=${this.cancelClick}></e-results-component>`;
    }
    
  }
}
