import '@eui/table';
import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './resultsComponent.css';
import GraphComponent from '../../../components/graph-component/src/GraphComponent';

@definition('e-results-component', {
  style,
  home: 'results-component',
  props: {
    answers: { attribute: true, type: Object },
    timestamp: { attribute: true, type: String }
  },
})
export default class ResultsComponent extends LitComponent {



  render() {
    return html`
    <div class="information-area">
    <h1>Well done! Questionaire complete!</h1>
    <e-graph-component 
    .answers=${this.answers}
    .timestamp=${this.timestamp}
    ></e-graph-component>
    <eui-base-v0-button icon="home"
        @click="${() => this.parentCallbackForHome()}"
        primary
        >Home</eui-base-v0-button>
`;
  }
}

ResultsComponent.register();