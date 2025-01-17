/*
 * Copyright ©1998-2023 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, version 2.0. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, version 2.0.
 */

package gurps

import (
	"github.com/richardwilkes/gcs/v5/model/fxp"
	"github.com/richardwilkes/toolbox/i18n"
	"github.com/richardwilkes/toolbox/xio"
)

var _ Bonus = &ConditionalModifierBonus{}

// ConditionalModifierBonus holds the data for a conditional modifier bonus.
type ConditionalModifierBonus struct {
	Type      FeatureType `json:"type"`
	Situation string      `json:"situation,omitempty"`
	LeveledAmount
	BonusOwner
}

// NewConditionalModifierBonus creates a new ConditionalModifierBonus.
func NewConditionalModifierBonus() *ConditionalModifierBonus {
	return &ConditionalModifierBonus{
		Type:          ConditionalModifierFeatureType,
		Situation:     i18n.Text("triggering condition"),
		LeveledAmount: LeveledAmount{Amount: fxp.One},
	}
}

// FeatureType implements Feature.
func (c *ConditionalModifierBonus) FeatureType() FeatureType {
	return c.Type
}

// Clone implements Feature.
func (c *ConditionalModifierBonus) Clone() Feature {
	other := *c
	return &other
}

// FillWithNameableKeys implements Feature.
func (c *ConditionalModifierBonus) FillWithNameableKeys(m map[string]string) {
	Extract(c.Situation, m)
}

// ApplyNameableKeys implements Feature.
func (c *ConditionalModifierBonus) ApplyNameableKeys(m map[string]string) {
	c.Situation = Apply(c.Situation, m)
}

// SetLevel implements Bonus.
func (c *ConditionalModifierBonus) SetLevel(level fxp.Int) {
	c.Level = level
}

// AddToTooltip implements Bonus.
func (c *ConditionalModifierBonus) AddToTooltip(buffer *xio.ByteBuffer) {
	c.basicAddToTooltip(&c.LeveledAmount, buffer)
}
