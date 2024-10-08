/**
 * Integration tests for <e-results-component>
 */
import { expect } from 'chai';
import '../ResultsComponent';
import {
  inShadow,
  injectHTMLElement,
  simulateEvent,
  nextTick
} from '../../../../../test/utils';

describe('ResultsComponent Component Tests', () => {
    let container;
    let inject;
    before(() => {
      container = document.body.appendChild(document.createElement('div'));
      inject = injectHTMLElement.bind(null, container);
    });

    after(() => {
      document.body.removeChild(container);
    });

    describe('Basic component setup', () => {
      it('should create a new <e-results-component>', async () => {
        const customElement = await inject('<e-results-component></e-results-component>');
        // check shadow DOM
        const headingTag = inShadow(customElement, 'h1');
        expect(headingTag.textContent, '"Your component markup goes here" was not found').to.equal('Your component markup goes here');
      });
    });
});
