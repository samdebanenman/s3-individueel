describe('Product List', () => {
  beforeEach(() => {
    cy.intercept('GET', '**/api/products**', { fixture: 'products.json' }).as('getProducts');
    cy.intercept('GET', '**/api/categories', { fixture: 'categories.json' }).as('getCategories');
    cy.visit('/ProductList');
  });

  it('should display products and categories', () => {
    cy.wait('@getProducts');
    cy.wait('@getCategories');

    // Check if the title is displayed
    cy.get('h1').should('contain', 'Product List');

    // Check if categories are loaded
    cy.get('select').children().should('have.length.greaterThan', 1);

    // Check if products are loaded
    cy.get('ul').children().should('have.length.greaterThan', 0);
  });

  it('should filter products by name', () => {
    cy.wait('@getProducts');
    cy.get('input[placeholder="Filter by name"]').type("Wilson Balls");

    // Check if only the filtered product is displayed
    cy.get('ul').children().should('have.length', 1);
    cy.get('ul').children().first().should('contain', 'Wilson Balls');
  });

  it('should filter products by category', () => {
    cy.wait('@getCategories');
    cy.get('select').select('Tennis Rackets');

    // Check if the filtered products are displayed
    cy.get('ul').children().should('have.length', 1);
    cy.get('ul').children().first().should('contain', 'Tennis racket');
  });
});
